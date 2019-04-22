#以下shell脚本用于在本地构建docker镜像，并将镜像推送到阿里云私有镜像仓库
#注意在windows下需要在Docker QuickStart Terminal里面使用命令 bash build.sh 这个命令来执行该脚本
#!/usr/bin/env/bash

mvn clean package -Dmaven.test.skip=true -U

docker build -t registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/config .

docker login --username=大叔在路上1988 registry.cn-shenzhen.aliyuncs.com

docker push registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/config