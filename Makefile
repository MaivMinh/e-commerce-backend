proto:
	protoc --proto_path=./app/cart-service/src/main/proto --java_out=./app/cart-service/src/main/java --grpc-java_out=./app/cart-service/src/main/java ./app/cart-service/src/main/proto/*.proto
	protoc --proto_path=./app/product-service/src/main/proto --java_out=./app/product-service/src/main/java --grpc-java_out=./app/product-service/src/main/java ./app/product-service/src/main/proto/*.proto
	protoc --proto_path=./app/auth-service/src/main/proto --java_out=./app/auth-service/src/main/java --grpc-java_out=./app/auth-service/src/main/java ./app/auth-service/src/main/proto/*.proto
	protoc --proto_path=./app/api-gateway/src/main/proto --java_out=./app/api-gateway/src/main/java --grpc-java_out=./app/api-gateway/src/main/java ./app/api-gateway/src/main/proto/*.proto
