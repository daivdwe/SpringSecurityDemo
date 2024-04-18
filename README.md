# Test Spring Boot with Spring Security.

Follow ??: (custom TODOs) for ordered instructions.

## Reverse proxy with nginx to test Filter:

> - install nginx (pref on wsl)
> - sudo nano /etc/nginx/conf.d/tomcat.conf
> ```
> server {
>   listen          8042;
>   server_name     localhost;
> 
>   location / {
>       proxy_set_header X-Forwarded-Host $host;
>       proxy_set_header X-Forwarded-Server $host;
>       proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
>       proxy_pass http://localhost:8080;
>   }
> }
> ```
> - start: sudo systemctl start nginx
> - Test on localhost:8042
> - stop: sudo systemctl stop nginx
