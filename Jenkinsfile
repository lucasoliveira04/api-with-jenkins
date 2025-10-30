pipeline {
    agent any

    triggers {
        pollSCM('H/5 * * * *')
    }

    environment {
        GIT_CREDENTIALS_ID = 'github-credentials' 
        GIT_REPO = 'https://github.com/lucasoliveira04/api-with-jenkins.git'
        BRANCH_SOURCE = 'main'
        BRANCH_TARGET = 'hml'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH_SOURCE}",
                    credentialsId: "${GIT_CREDENTIALS_ID}",
                    url: "${GIT_REPO}"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Sync main → hml') {
            steps {
                script {
                    sh """
                        git config user.name "jenkins"
                        git config user.email "jenkins@localhost"
                        git fetch origin ${BRANCH_TARGET}
                        git checkout ${BRANCH_TARGET}
                        git merge origin/${BRANCH_SOURCE} -m "Auto-sync main → hml [Jenkins]"
                        git push origin ${BRANCH_TARGET}
                    """
                }
            }
        }
    }

    post {
        failure {
            echo 'O pipeline falhou. Verifique os logs.'
        }
        success {
            echo 'main sincronizada com hml com sucesso!'
        }
    }
}
