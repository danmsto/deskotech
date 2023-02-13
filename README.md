# deskotech

Requires a MySQL database for server-side, run npm install for client-side.

Also requires an application.properties file in server/src/resources/ with the following:

    spring.datasource.driver-class-name=com.mysql.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/desk-booking?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.datasource.username=<INSERT_ROOT_USERNAME>
    spring.datasource.password=<INSERT_ROOT_PASSWORD>

    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username=deskbuddy.wandisco@gmail.com
    spring.mail.password=xyorpojvgctoynqg
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true

    rsa.rsa-private-key=classpath:certs/private.pem
    rsa.rsa-public-key=classpath:certs/public.pem

    admin.username=admin
    admin.password=LordOfTheDesks
