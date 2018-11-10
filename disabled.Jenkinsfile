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
                sh 'mvn clean install -Pfrontend-clean'
                archiveArtifacts 'server/target/*.jar'
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
    }
}