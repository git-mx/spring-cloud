mvn clean package -Dmaven.test.skip=true -U

docker build -t registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/product .

docker login --username=大叔在路上1988 registry.cn-shenzhen.aliyuncs.com

docker push registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/product