# 测试注册接口，registdata.json文件存储的是注册post请求的 json 格式数据（RegisterDto）
curl -H "Content-Type: application/json" -X POST -d @registdata.json http://localhost:13537/api/user/register
## 执行结果：返回包含token和 tokenType的数据结构：
## {"accessToken":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6aGFvamZAMTM5LmNvbSIsInJvbGUiOlsiVVNFUiJdLCJpYXQiOjE2OTk0MTQzNTksImV4cCI6MTY5OTQ1MDM1OX0.TygfocoY6bgClyZULaP606D249jm30wycWWoEKgJY_k","tokenType":"Bearer "}

# 测试admin用户登录认证接口，logindata.json文件中存储的是登录认证请求的 json 格式数据（LoginDto）
curl -H "Content-Type: application/json" -X POST -d @logindata.json http://localhost:13537/api/user/authenticate
## 执行结果：返回token字符串
## eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzgwMTAwMTIzNCIsInJvbGUiOlsiQURNSU4iXSwiaWF0IjoxNjk5NDI3MDQyLCJleHAiOjE2OTk0NjMwNDJ9.9FmVQ-4wArTqU5PDoDbeyn0Dms6fFDXDMR08EZwpQ18

# 测试 admin用户携带 token 访问接口。token放在请求头中，以'Authorization'为键，以'Bearer token '为值
curl -H "Content-Type: application/json" \
     -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzgwMTAwMTIzNCIsInJvbGUiOlsiQURNSU4iXSwiaWF0IjoxNjk5NDI3MDQyLCJleHAiOjE2OTk0NjMwNDJ9.9FmVQ-4wArTqU5PDoDbeyn0Dms6fFDXDMR08EZwpQ18" \
     http://localhost:13537/api/admin/hello


# 测试超级管理员 superadmin 用户登录认证接口，superlogindata.json文件中存储的是登录认证请求的 json 格式数据（LoginDto）
curl -H "Content-Type: application/json" -X POST -d @superlogindata.json http://localhost:13537/api/user/authenticate
## 执行结果：返回token字符串
## eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzkwMTAwMTIzNCIsInJvbGUiOlsiU1VQRVJBRE1JTiJdLCJpYXQiOjE2OTk0MjcxNTksImV4cCI6MTY5OTQ2MzE1OX0.c8vageBpJjiwZl4jk0MbLZt02fBfQwD-cn6_malqnr8

# 测试 超级管理员携带 token 访问接口。token放在请求头中，以'Authorization'为键，以'Bearer token '为值
curl -H "Content-Type: application/json" \
     -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMzkwMTAwMTIzNCIsInJvbGUiOlsiU1VQRVJBRE1JTiJdLCJpYXQiOjE2OTk0MjcxNTksImV4cCI6MTY5OTQ2MzE1OX0.c8vageBpJjiwZl4jk0MbLZt02fBfQwD-cn6_malqnr8" \
     http://localhost:13537/api/superadmin/hi
