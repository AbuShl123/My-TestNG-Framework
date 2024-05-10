def tc_total
def tc_failed
def tc_skipped
def tc_passed
def date

pipeline {
    agent any

    tools {
        maven "maven-3.8.6"
    }

    stages {
        stage('Git') {
            steps {
                git branch: 'master', credentialsId: 'Jenkins', url: 'https://github.com/...'
            }
        }

        stage('Clean') {
            steps {
                bat "clean.bat"
            }
        }

        stage('Regression') {
            steps {
                bat "mvn -D--is-on-jenkins=true clean compile package test"
            }
        }
    }

    post {
        always {
            step([$class: 'JUnitResultArchiver',
            testResults: 'target/surefire-reports/TEST-TestSuite.xml'])

            publishHTML([
                allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: false,
                        reportDir: '',
                        reportFiles: 'test-reports/for-jenkins.html',
                        reportName: 'Extent Report',
                        reportTitles: 'The Regression Report'])

            script {
                // Parse Surefire XML report to get the number of failed and passed tests
                def fileText = readFile 'target/surefire-reports/TEST-TestSuite.xml'
                def xmlFile = new XmlSlurper().parseText(fileText)

                tc_total = xmlFile.@tests.toInteger()
                tc_failed = xmlFile.@failures.toInteger()
                tc_skipped = xmlFile.@skipped.toInteger()

                tc_passed = tc_total - tc_failed - tc_skipped;

                echo "Total tests: $tc_total"
                echo "Failed tests: $tc_failed"
                echo "Skipped tests: $tc_skipped"
                echo "Passed tests: $tc_passed"

                date = new Date().format("MMMM d, yyyy", TimeZone.getTimeZone('UTC'));

                def webHookURL = "#"

                def json =
"""
{
  "summary": "Jenkins",
  "themeColor": "0078D7",
  "title": "Notification from Jenkins",
  "sections": [
    {
      "activityTitle": "Here is the Regression Report from $date:",
      "text": "Total:  $tc_total    |    Passed:  $tc_passed    |    Failed:  $tc_failed    |    Skipped:  $tc_skipped"
    }
  ],
  "potentialAction": [
    {
      "@type": "OpenUri",
      "name": "View Report",
      "targets": [
        { "os": "default", "uri": "http://localhost:8081/job/Regression/Reg_20Report/" }
      ]
    }
  ]
}
"""
                json = json.replaceAll("\"", "'")

                def command = "curl -H \"Content-Type:application/json\" -d \"$json\" $webHookURL" as String

                def out = command.execute().text

                echo "POST message response: $out"
            }
        }
    }
}
