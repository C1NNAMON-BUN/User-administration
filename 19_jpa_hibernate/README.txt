2. Use this line to build and run docker-compose file
sudo docker-compose up --build

2. Use url: localhost:8080 to connect to app

There are two users in the database:
1. Admin (login: admin, password: admin)
2. User (login: user, password: user)

In order to run the site not through docker, replace the lines:
         <property name="hibernate.connection.url">jdbc:h2:tcp://localhost/~/test</property>
         <property name="hibernate.connection.username">sa</property>
in hibernate.cfg.xml file

