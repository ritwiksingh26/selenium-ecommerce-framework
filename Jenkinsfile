pipeline {
    agent any

    parameters {
        choice(name: 'SUITE', choices: ['smoke', 'regression', 'api', 'regression-parallel'],
               description: 'Test suite (Maven profile) to run')
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'],
               description: 'Browser')
        choice(name: 'ENVIRONMENT', choices: ['dev', 'staging'],
               description: 'Environment config')
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
        timeout(time: 45, unit: 'MINUTES')
    }

    stages {
        stage('Build') {
            steps {
                script { mvn('clean compile -q') }
            }
        }

        stage('Test') {
            steps {
                script {
                    mvn("test -P${params.SUITE} -Dbrowser=${params.BROWSER} " +
                        "-Denv=${params.ENVIRONMENT} -Dheadless=true")
                }
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'test-output/reports/**/*.html, test-output/screenshots/**/*.png, logs/*.log',
                             allowEmptyArchive: true
            allure results: [[path: 'target/allure-results']]
        }
        success { echo 'Pipeline passed' }
        failure { echo 'Pipeline failed' }
    }
}

// Runs the same Maven command on Windows or Linux agents
def mvn(String args) {
    if (isUnix()) { sh "mvn ${args}" } else { bat "mvn ${args}" }
}