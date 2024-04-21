pipeline {
    agent any
    environment {
        REGISTRY = 'alexintelc/ordermanager'
        REGISTRY_CREDENTIAL = 'dockerhub'
        AWS_CREDENTIAL = '456bd977-adfc-47f9-9ccb-0430011f0767'
        EC2_IP = '34.245.3.122'
    }
    stages {
        stage('100-Checkout(SCM)') {
            steps {
                echo '[INFO] > 100-Checkout(SCM) > Checking out Source Code Management...'
                checkout scm
                echo '[INFO] > 100-Checkout(SCM) > Checkout completed!!'
            }
        }

        stage('200-Build') {
            steps {
                echo '[INFO] > 200-Build > Building the image...'
                bat 'mvn -B -DskipTests clean package'
                echo '[INFO] > 200-Build > Build phase completed!!'
            }
        }

        stage('201-Test') {
            steps {
                echo '[INFO] > 201-Test > Running tests...'
 				bat 'mvn clean test -Dspring.profiles.active=standalone'
                echo '[INFO] > 201-Test > Test phase completed!!'
            }
        }

        stage('300-Build Docker Image') {
            steps {
                echo '[INFO] > 300-Build Docker Image > Building Docker image...'
                bat 'docker build -t %REGISTRY%:latest .'
                echo '[INFO] > 300-Build Docker Image > Docker image build completed!!'
            }
        }

        stage('301-Publish Docker Image') {
            steps {
                echo '[INFO] > 301-Publish Docker Image > Publishing Docker image...'
                withCredentials([usernamePassword(credentialsId: REGISTRY_CREDENTIAL, passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
                    bat 'docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%'
                    bat 'docker push %REGISTRY%:latest'
                }
                echo '[INFO] > 301-Publish Docker Image > Docker Image has been published!!'
            }
        }
        
        stage('302-Clean up Docker Image') {
            steps {
                echo "[INFO] > 302-Clean up Docker Image > Cleaning up Docker image after it has been pushed to Dockerhub..."
                bat "docker rmi %REGISTRY%:latest"
                echo "[INFO] > 302-Clean up Docker Image > Docker image has been cleaned up!!"
            }
        }
        
		stage('400-Deploy to Cloud') {
		    steps {
		        echo "[INFO] > 400-Deploy to Cloud > Deploying Docker image to AWS EC2 instance..."
		        withCredentials([sshUserPrivateKey(credentialsId: 'AWS_SSH_CREDENTIAL', keyFileVariable: 'SSH_PRIVATE_KEY', passphraseVariable: '', usernameVariable: 'SSH_USER')]) {
		            script {
		                def remoteCommand = '''
		                    ssh -i "%SSH_PRIVATE_KEY%" %SSH_USER%@%EC2_IP% 'docker pull alexintelc/ordermanager:latest && docker-compose up -d'
		                '''
		                sh remoteCommand
		            }
		        }
		        echo "[INFO] > 400-Deploy to Cloud > Docker image deployed successfully to AWS EC2 instance!"
		    }
		}
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}
