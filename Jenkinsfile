pipeline {
    agent any
    environment {
        REGISTRY = 'alexintelc/ordermanager'
        REGISTRY_CREDENTIAL = 'dockerhub'
        EC2_USERNAME = 'ec2-user'
        EC2_HOST = 'ec2-3-249-160-233.eu-west-1.compute.amazonaws.com'        
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
        
		stage('303-Deploy to EC2') {
		    steps {
		        echo "[INFO] > 303-Deploy to EC2 > Deploying to EC2 instance..."
		        withCredentials([sshUserPrivateKey(credentialsId: 'EC2', keyFileVariable: 'EC2_KEY', usernameVariable: 'EC2_USER')]) {
    				bat "scp docker-compose.yml %EC2_USERNAME%@%EC2_HOST%:~/docker-compose.yml"
		            bat "ssh %EC2_USERNAME%@%EC2_HOST% 'cd ~ && docker-compose up -d'"
				}
		        echo "[INFO] > 303-Deploy to EC2 > Deployment to EC2 instance completed!!"
		    }
		}
    }

    post {
        always {
            bat 'docker logout'
        }
    }
}