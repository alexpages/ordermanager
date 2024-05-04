pipeline {
    agent any
    environment {
        REGISTRY = 'alexintelc/ordermanager'
        REGISTRY_CREDENTIAL = 'dockerhub'
        EC2_USERNAME = 'ec2-user'
        EC2_HOST = '52.215.15.168'   
        DOCKER_COMPOSE_YML = 'C:\\Users\\alexp\\git\\ordermanager\\docker-compose.yml'   
        EC2_CERTIFICATE = 'C:\\Users\\alexp\\Desktop\\ordermanager\\ordermanager.pem'
        ENV_FILE = 'C:\\Users\\alexp\\git\\ordermanager\\.env' 
    }
    stages {
        stage('1-Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('2-Build') {
            steps {
                bat "mvn -B -DskipTests clean package"
            }
        }

        stage('3-Test') {
            steps {
                bat "mvn clean test -Dspring.profiles.active=standalone"
            }
        }

        stage('4-Build Docker Image') {
            steps {
                bat "docker build -t %REGISTRY%:latest ."
            }
        }

        stage('5-Publish Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIAL, passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                    bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
                    bat "docker push %REGISTRY%:latest"
                }
            }
        }
        
        stage('6-Clean up Docker Image') {
            steps {
                bat "docker rmi %REGISTRY%:latest"
            }
        }
        
        stage('7-Deploy to EC2') {
            steps {
                withCredentials([sshUserPrivateKey(credentialsId: 'EC2', keyFileVariable: 'EC2_KEY', usernameVariable: 'EC2_USER')]) {
                 	bat "ssh -i %EC2_CERTIFICATE% %EC2_USERNAME%@%EC2_HOST% \"sudo systemctl start docker\""
                    bat "scp -i %EC2_CERTIFICATE% %DOCKER_COMPOSE_YML% %EC2_USERNAME%@%EC2_HOST%:~/docker-compose.yml"
                    bat "scp -i %EC2_CERTIFICATE% %ENV_FILE% %EC2_USERNAME%@%EC2_HOST%:~/.env"
					bat 'ssh -i %EC2_CERTIFICATE% %EC2_USERNAME%@%EC2_HOST% "cd ~/ && docker-compose pull && docker-compose up -d && docker image prune -a -f"'
                }
            }
        }
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}
