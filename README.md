# rcs
Robot Configuration System

## Backend
Multimodular application  
Tech stack:  
* Java 11;
* AWS SDK;
* AWS Layer;
* AWS Lambda;
* AWS DynamoDB.
### Compile
Execute following command in the root directory
`mvn clean package shade:shade`
### Packaging
Compress the `service-layer-{project-version}-shaded.jar` in an archive `service-layer.zip` with the structure `java/lib/service-layer-{project-version}-shaded.jar`
### Deploying
Using CLI:
1. Upload this ZIP file into the desired S3 bucket.  
`aws s3 cp service-layer.zip s3://<BUCKET_NAME>`
2. Create a new lambda layer using aws CLI and save the response.  
`aws lambda publish-layer-version --layer-name <LAYER_NAME>\  
--compatible-runtimes java8\  
--content S3Bucker=<BUCKET_NAME>,S3Key=service-layer.zip`  
3. Create Lambda functions using newly created layer.  
`aws lambda create-fucntion --function-name <FUNCTION_NAME>\  
--runtime java11 --role <ROLE_ARN> --handler <PATH_TO_HANDLER_CLASS>\  
--memory-size <MEMORY_SIZE_IN_MB> --region <REGION> --layers <LAYER_ARN>\    
--zip-file fileb://<PATH_TO_JAR> --publish`  

Or you can created both with Amazon web interface  
[More info](https://docs.aws.amazon.com/lambda/latest/dg/configuration-layers.html)
