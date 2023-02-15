# Bloggingapp

## Set up database

### Linux

#### Install postgresql
```shell
sudo apt update
sudo apt install postgresql postgresql-contrib
```

#### Create user and database
```postgresql
CREATE DATABASE blog
CREATE USER blog_user WITH PASSWORD 'blog_password'
GRANT ALL PRIVILEGES TO blog_user ON DATABASE blog
```