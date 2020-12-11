file_name="zjsjcy-task-0.0.1-SNAPSHOT"
app_name="${file_name}.jar"
echo ${app_name}启动中...
pid=$(ps -ef|grep ${app_name}|grep -v "grep"|awk '{print $2}')
if [ -n "${pid}" ];
then
  echo "The ${app_name}[pid:${pid}] is running,restart..."
  kill -9 ${pid}
else
  echo "${app_name} starting..."
fi
nohup java -Djava.security.egd=file:/dev/../dev/urandom -Xms5g -Xmx5g -jar ${app_name} --spring.profiles.active=test > /dev/null 2>&1 &
