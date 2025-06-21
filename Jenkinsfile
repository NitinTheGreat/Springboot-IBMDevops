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
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:${IMAGE_TAG}")
                }
            }
        }

        // Optional: Push Docker image to DockerHub or another registry
        // Uncomment and configure the following stage if you want to push:
        /*
        stage('Push to Docker Registry') {
            steps {
                withDockerRegistry([credentialsId: 'docker-hub-creds', url: '']) {
                    dockerImage.push()
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

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
