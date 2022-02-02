# Rekkme-Backend

## Build and Run ##

Windows
```
.\ps-init.ps1
```

Linux
```
./unix-init.sh
```

## Hit Endpoints Locally ##
http://localhost:8080/rekkme/api

## Connect the database (Docker Container: rekkme-backend/rekkme-db) ##
```
psql -h localhost -p 5432 -U compose-postgres -W
```

## Push App to Heroku ##
```
heroku stack:set container --app <heroku-app-name>
git add .
git commit -m "."
git push
```

## Kill all connections to Heroku DB ##
```
heroku pg:killall --app="rekkme-backend-app"
```