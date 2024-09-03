#!/bin/sh
echo "Init localstack"
awslocal s3api create-bucket --bucket ai-blog-draft