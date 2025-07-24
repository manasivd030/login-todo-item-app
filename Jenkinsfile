pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6'
        jdk 'JDK 17'
        nodejs 'Node 20'
    }

    environment {
        BACKEND_DIR = 'Login/backend'
        FRONTEND_DIR = 'Login/frontend'
        TESTS_DIR = 'Login/tests'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/your-repo.git'
            }
        }

        stage('Install Backend Dependencies') {
            steps {
                dir(env.BACKEND_DIR) {
                    sh 'npm install'
                }
            }
        }

        stage('Start Backend Server') {
            steps {
                dir(env.BACKEND_DIR) {
                    sh 'nohup npm start &'  // Run backend in background
                }
            }
        }

        stage('Build Frontend') {
            steps {
                dir(env.FRONTEND_DIR) {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }

        stage('Run API Tests') {
            steps {
                dir(env.TESTS_DIR) {
                    sh 'mvn clean test jacoco:report'
                }
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false, jdk: '', results: [[path: "Login/tests/target/allure-results"]]
            }
        }

        stage('Archive Code Coverage Report') {
            steps {
                archiveArtifacts artifacts: 'Login/tests/target/site/jacoco/index.html', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            junit 'Login/tests/target/surefire-reports/*.xml'
        }
    }
}