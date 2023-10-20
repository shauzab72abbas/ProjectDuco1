#!groovy

@Library('do-jenkins-shared-libraries@master')

// service details
def serviceName = 'erpfi-suppliers'

// helm chart details
def chartName = 'do-container-deployment'
def chartVersion = '0.1.16'

// namespace details
def namespaceDev = 'erpfi-dev'
def namespaceTest = 'erpfi-test'
def namespaceProd = 'erpfi-prod'

def docker_registry_url = 'https://index.docker.io/v1/'
def docker_registry_cred_id = 'f0df4430-f37f-4133-9746-8eb07fcf2165'

def kubernetes_deploy_agent = '''
spec:
  containers:
  - name: helm
    image: twgorg/do-jenkinsk8sagent:helm3.1.1baseimage
    command:
    - cat
    tty: true
  imagePullSecrets:
  - name: regsecret
'''

pipeline {
    agent none

    options {
        timestamps()
        buildDiscarder(logRotator(numToKeepStr: '20'))
        skipStagesAfterUnstable()
        timeout time: 30, unit: 'MINUTES'
    }

    parameters {
        booleanParam(
        name: 'DEPLOY_TO_PROD',
        defaultValue: false,
        description: '')
        booleanParam(
        name: 'DEPLOY_TO_TEST',
        defaultValue: false,
        description: '')
        string(
        name: 'APP_COMMIT_VERSION',
        defaultValue: 'DEFAULT',
        description: 'Commit version of the app to deploy, this is only valid if DEPLOY_TO_PROD is selected. Allows to deploy an older build with the current deployment values. Preffered method is selecting "Restart from Stage" to use same commit for build and deploy'
    )
    }

    stages {
        stage('Build') {
            when {
                beforeOptions true
                beforeAgent true
                not {
                    expression { return params.DEPLOY_TO_PROD }
                }
            }
            agent {
                label 'build-agent'
            }
            environment {
                NEXUS_CREDS = credentials('do-nexus-credentials')
                NEXUS_REPO_BASE_URL_ARTIFACTS = 'https://nexus-dev-aws.twg.co.nz/repository/components'
            }
            steps {
                script {
                    // pre build kubernetes cleanup
                    sh "kubectl delete --all deployments,services,configmaps"
                
                    withDockerRegistry([credentialsId: docker_registry_cred_id, url: '']) {
                        docker.image('maven:3.8-openjdk-11').inside('-v /var/run/docker.sock:/var/run/docker.sock -v /home/ec2-user/m2cache:/root/.m2 -v /home/ec2-user/.kube:/root/.kube --network host') {
                            echo "appversion is ${env.GIT_COMMIT}."
                            sh "KUBECONFIG=/root/.kube/config mvn -Drevision=${env.GIT_COMMIT} clean verify -Pjkube -s ./settings.xml"
                        }
                        if ("${env.BRANCH_NAME}" == 'master') {
                            print 'Pushing docker image for master branch'
                            sh "docker push twgorg/${serviceName}:${env.GIT_COMMIT}"
                        }
                    }
                }
            }
            post {
                always {
                    script {
                        // post build kubernetes cleanup
                        sh "kubectl delete --all deployments,services,configmaps"
                        
                        setBuildDetails(env.BUILD_NUMBER, env.GIT_COMMIT, params.APP_COMMIT_VERSION)
                        uploadArtifactsToNexus(env.NEXUS_REPO_BASE_URL_ARTIFACTS, env.JOB_NAME, env.BUILD_NUMBER) // NEXUS_CRED env variable is expected too
                    }
                }
            }
        }

        stage('Deploy to Dev & Verify') {
            options {
                lock resource: "${env.JOB_NAME}-dev", quantity: 1, variable: 'deployEnv'
            }
            when {
                beforeOptions true
                beforeAgent true
                expression { env.BRANCH_NAME == 'master' }
                not {
                    expression { return params.DEPLOY_TO_PROD }
                }
            }
            agent {
                kubernetes {
                    yaml kubernetes_deploy_agent
                }
            }
            environment {
                NEXUS_CREDS = credentials('do-nexus-credentials')
                NEXUS_REPO_BASE_URL = 'https://nexus-dev-aws.twg.co.nz/repository/do-helm-charts/'
            }
            steps {
                container('helm') {
                    script {
                        withCredentials([file(credentialsId: 'AKS_dev_cluster_kubeconfig', variable: 'KUBECONFIG')]) {
                            environment = 'dev'
                            namespace = "${namespaceDev}"
                            chartDownloadDirectory = "./helm-chart-${chartName}-${chartVersion}"
                            chartDirectory = "./${chartDownloadDirectory}/${chartName}"

                            buildVersion = "${env.GIT_COMMIT}"

                            // Remove non-ascii (unicode) invisible characters, can come in if doing a copy/paste from html pages e.g. Jenkins build description page
                            buildVersion = buildVersion.replaceAll("[^\\x00-\\x7F]", '')

                            sh "helm pull ${chartName} --version ${chartVersion} --untar --untardir ${chartDownloadDirectory} --repo ${env.NEXUS_REPO_BASE_URL} --username ${env.NEXUS_CREDS_USR} --password ${env.NEXUS_CREDS_PSW}"

                            print "Deploying ${buildVersion} to namespace ${namespace} for env ${environment}"
                            sh "helm --namespace ${namespace} ls"

                            print 'Doing dry run'
                            sh "helm upgrade --dry-run --debug --install --namespace ${namespace} -f deploy/${environment}/values.yaml --set-string appVersion=${buildVersion} ${serviceName} ${chartDirectory}"

                            print 'Applying helm chart'
                            sh "helm upgrade --atomic --wait --debug --install --namespace ${namespace} -f deploy/${environment}/values.yaml --set-string appVersion=${buildVersion} ${serviceName} ${chartDirectory}"
                        }
                    }
                }
            }
            post {
                always {
                    script {
                        setBuildDetails(env.BUILD_NUMBER, env.GIT_COMMIT, params.APP_COMMIT_VERSION)
                    }
                }
            }
        }

        stage('Deploy to Test & Verify') {
            options {
                lock resource: "${JOB_NAME}-test", quantity: 1, variable: 'deployEnv'
            }
            when {
                beforeOptions true
                beforeAgent true
                expression { BRANCH_NAME == 'master' }
                anyOf {
                    expression { return params.DEPLOY_TO_TEST }
                    expression { return isBuildCauseStageRestartFrom('Deploy to Test & Verify', currentBuild) }
                }
            }
            agent {
                kubernetes {
                    yaml kubernetes_deploy_agent
                }
            }
            environment {
                NEXUS_CREDS = credentials('do-nexus-credentials')
                NEXUS_REPO_BASE_URL = 'https://nexus-dev-aws.twg.co.nz/repository/do-helm-charts/'
            }
            steps {
                container('helm') {
                    script {
                        withCredentials([file(credentialsId: 'AKS_test_cluster_kubeconfig', variable: 'KUBECONFIG')]) {
                            environment = 'test'
                            namespace = "${namespaceTest}"
                            chartDownloadDirectory = "./helm-chart-${chartName}-${chartVersion}"
                            chartDirectory = "./${chartDownloadDirectory}/${chartName}"

                            buildVersion = "${env.GIT_COMMIT}"

                            // Remove non-ascii (unicode) invisible characters, can come in if doing a copy/paste from html pages e.g. Jenkins build description page
                            buildVersion = buildVersion.replaceAll("[^\\x00-\\x7F]", '')

                            sh "helm pull ${chartName} --version ${chartVersion} --untar --untardir ${chartDownloadDirectory} --repo ${env.NEXUS_REPO_BASE_URL} --username ${env.NEXUS_CREDS_USR} --password ${env.NEXUS_CREDS_PSW}"

                            print "Deploying ${buildVersion} to namespace ${namespace} for env ${environment}"
                            sh "helm --namespace ${namespace} ls"

                            print 'Doing dry run'
                            sh "helm upgrade --dry-run --debug --install --namespace ${namespace} -f deploy/${environment}/values.yaml --set-string appVersion=${buildVersion} ${serviceName} ${chartDirectory}"

                            print 'Applying helm chart'
                            sh "helm upgrade --atomic --wait --debug --install --namespace ${namespace} -f deploy/${environment}/values.yaml --set-string appVersion=${buildVersion} ${serviceName} ${chartDirectory}"
                        }
                    }
                }
            }
            post {
                always {
                    script {
                        setBuildDetails(env.BUILD_NUMBER, env.GIT_COMMIT, params.APP_COMMIT_VERSION)
                    }
                }
            }
        }

        stage('Deploy to Prod & Verify') {
            options {
                lock resource: "${JOB_NAME}-prod", quantity: 1, variable: 'deployEnv'
            }
            when {
                beforeOptions true
                beforeAgent true
                expression { BRANCH_NAME == 'master' }
                anyOf {
                    expression { return params.DEPLOY_TO_PROD }
                    expression { return isBuildCauseStageRestartFrom('Deploy to Prod & Verify', currentBuild) }
                }
            }
            agent {
                kubernetes {
                    yaml kubernetes_deploy_agent
                }
            }
            environment {
                NEXUS_CREDS = credentials('do-nexus-credentials')
                NEXUS_REPO_BASE_URL = 'https://nexus-dev-aws.twg.co.nz/repository/do-helm-charts/'
            }
            steps {
                container('helm') {
                    script {
                        withCredentials([file(credentialsId: 'AKS_prod_cluster_kubeconfig', variable: 'KUBECONFIG')]) {
                            environment = 'prod'
                            namespace = "${namespaceProd}"
                            chartDownloadDirectory = "./helm-chart-${chartName}-${chartVersion}"
                            chartDirectory = "./${chartDownloadDirectory}/${chartName}"

                            buildVersion = "${env.GIT_COMMIT}"
                            if ("${params.APP_COMMIT_VERSION}" != 'DEFAULT') {
                                buildVersion = "${params.APP_COMMIT_VERSION}"
                            }

                            // Remove non-ascii (unicode) invisible characters, can come in if doing a copy/paste from html pages e.g. Jenkins build description page
                            buildVersion = buildVersion.replaceAll("[^\\x00-\\x7F]", '')

                            sh "helm pull ${chartName} --version ${chartVersion} --untar --untardir ${chartDownloadDirectory} --repo ${env.NEXUS_REPO_BASE_URL} --username ${env.NEXUS_CREDS_USR} --password ${env.NEXUS_CREDS_PSW}"

                            print "Deploying ${buildVersion} to namespace ${namespace} for env ${environment}"
                            sh "helm --namespace ${namespace} ls"

                            print 'Doing dry run'
                            sh "helm upgrade --dry-run --debug --install --namespace ${namespace} -f deploy/${environment}/values.yaml --set-string appVersion=${buildVersion} ${serviceName} ${chartDirectory}"

                            print 'Applying helm chart'
                            sh "helm upgrade --atomic --wait --debug --install --namespace ${namespace} -f deploy/${environment}/values.yaml --set-string appVersion=${buildVersion} ${serviceName} ${chartDirectory}"
                        }
                    }
                }
            }
            post {
                always {
                    script {
                        setBuildDetails(env.BUILD_NUMBER, env.GIT_COMMIT, params.APP_COMMIT_VERSION)
                    }
                }
            }
        }
    }
}
