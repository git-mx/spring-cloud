#以下shell脚本用于在本地构建docker镜像，并将镜像推送到阿里云私有镜像仓库
#注意在windows下需要在Docker QuickStart Terminal里面使用命令 bash build.sh 这个命令来执行该脚本
#!/usr/bin/env/bash

#重新生成jar包
mvn clean package -Dmaven.test.skip=true -U

#构建docker镜像，如果不命名成registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/eureka
#那么，还需要tag重命名
docker build -t registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/eureka .

#登录阿里云私有镜像库
docker login --username=大叔在路上1988 registry.cn-shenzhen.aliyuncs.com

#如果没有明明成registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/eureka则需要以下命令
#其中的ImageId是镜像的id，可以用过docker images 来查看
#docker tag [ImageId] registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/eureka

#将镜像推送到阿里云私有镜像库
docker push registry.cn-shenzhen.aliyuncs.com/springcloud-namespace/eureka