pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'JDK8'
    }
    stages {
        stage('Build') {
            steps {
                echo "Building on branch " + env.BRANCH_NAME
                withCredentials([file(credentialsId: 'portfolio-prd-properties', variable: 'PORTFOLIO_PROPERTIES')]) {
                    sh 'cp $PORTFOLIO_PROPERTIES ./server/src/main/resources/application.properties'
                    sh 'mvn clean install -Pfrontend-clean'
                    archiveArtifacts 'server/target/*.war'
                }
            }
        }
        stage('Deploy Development') {
            when { branch 'develop' }
            steps {
                echo "Deploying on stage: dev"
                withCredentials([
                    string(credentialsId: 'aws-deploy-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-deploy-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh 'npm run deploy:dev'
                }
            }
        }
        stage('Deploy Production') {
            when { branch 'master' }
            steps {
                echo "Deploying on stage: prd"
                withCredentials([
                    string(credentialsId: 'aws-deploy-key-id', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-deploy-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh 'npm run deploy:prd'
                }
            }
        }
        post {
            always {
                cleanWs()
            }
        }
    }
}