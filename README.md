# Testing Spring Boot with Spring Security

Many thanks to [Daniel Garnier-Moiroux](https://github.com/Kehrlann) whose presentation of
[spring-security-the-good-parts](https://github.com/Kehrlann/spring-security-the-good-parts) at JavaLand 2024
inspired me to create this demo.

## Branches

- [master](https://github.com/daivdwe/SpringSecurity): a "secure" spring security demo application
- [sec-demo-mode](https://github.com/daivdwe/SpringSecurity/tree/sec-demo-mode): demo mode of master -
  follow ??: (custom TODO) in order and uncomment / reimplement
- [no-sec-base](https://github.com/daivdwe/SpringSecurity/tree/no-sec-base): the "base" of this project - a
  spring project without any security
- [proxy-issues](https://github.com/daivdwe/SpringSecurity/tree/proxy-issues): (open) proxy issue I encountered
  at work tying to use this project behind a proxy server

## Reverse proxy with nginx to test Filter

- install nginx (preferably with WSL or another linux (sub-) system)
- sudo nano /etc/nginx/conf.d/tomcat.conf
- add / replace with following config and save:

 ```
 server {
   listen          8042;
   server_name     localhost;
 
   location / {
       proxy_set_header X-Forwarded-Host $host;
       proxy_set_header X-Forwarded-Server $host;
       proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
       proxy_pass http://localhost:8080;
   }
 }
 ```

- start reverse proxy: sudo systemctl start nginx
- Test on localhost:8042
- stop reverse proxy: sudo systemctl stop nginx
