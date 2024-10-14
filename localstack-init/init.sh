#!/bin/sh
echo "Init localstack"
awslocal s3api create-bucket --bucket ai-blog-draft
awslocal sqs create-queue --queue-name draft-content-job-queue