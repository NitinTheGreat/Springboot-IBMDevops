pipeline {
    agent any

    environment {
        IMAGE_NAME = "tasktracker"
        IMAGE_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh './mvnw clean package'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .'
            }
        }

        // Optional: Push Docker image to DockerHub or another registry
        // Uncomment and configure the following stage if you want to push:
        /*
        stage('Push to Docker Registry') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"
                    sh "docker logout"
                }
            }
        }
        */

        stage('Cleanup') {
            steps {
                sh 'docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true'
            }
        }
    }

    // If you want to run junit in post section for all stages, keep this:
    /*
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
    */
}
