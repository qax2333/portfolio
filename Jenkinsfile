pipeline {
    agent any
    tools {
        maven 'mvn-3.6'
        jdk 'oracle-jdk-8'
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
    }
    post {
        always {
            cleanWs()
        }
    }
}
