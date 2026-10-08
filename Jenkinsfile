pipeline {
    agent any

    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Browser to run tests on'
        )
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'staging'],
            description: 'Environment to test against'
        )
        choice(
            name: 'SUITE',
            choices: ['smoke', 'regression', 'api', 'regression-parallel'],
            description: 'Test suite to execute'
        )
    }

    environment {
        JAVA_HOME = '/usr/lib/jvm/java-21-openjdk'
        MAVEN_HOME = '/usr/share/maven'
        PATH = "${JAVA_HOME}/bin:${MAVEN_HOME}/bin:${PATH}"
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 60, unit: 'MINUTES')
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out branch: ${env.BRANCH_NAME}"
                checkout scm
            }
        }

        stage('Build & Compile') {
            steps {
                echo 'Compiling project...'
                sh 'mvn clean compile -q'
            }
        }

        stage('Run Tests') {
            steps {
                echo "Running suite: ${params.SUITE} | browser: ${params.BROWSER} | env: ${params.ENVIRONMENT}"
                sh """
                    mvn test \
                        -P${params.SUITE} \
                        -Dbrowser=${params.BROWSER} \
                        -Denv=${params.ENVIRONMENT} \
                        -Dheadless=true
                """
            }
            post {
                always {
                    // Archive screenshots
                    archiveArtifacts artifacts: 'test-output/screenshots/**/*.png',
                        allowEmptyArchive: true

                    // Archive Extent Reports
                    archiveArtifacts artifacts: 'test-output/reports/**/*.html',
                        allowEmptyArchive: true

                    // Publish Allure Report
                    allure([
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'target/allure-results']]
                    ])
                }
            }
        }

        stage('Code Quality — SonarQube') {
            when {
                branch 'main'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                            -Dsonar.projectKey=selenium-ecommerce-framework
                    """
                }
            }
        }

        stage('Quality Gate') {
            when {
                branch 'main'
            }
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline completed successfully'
            // Add Slack notification here when token is configured
        }
        failure {
            echo '❌ Pipeline failed'
            // Add Slack notification here when token is configured
        }
        always {
            cleanWs() // Clean workspace after each run
        }
    }
}