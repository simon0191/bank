
%%. [Descargar Gigaspaces](http://www.gigaspaces.com/xap-download)
%%. Installar [plugin de Gigaspaces para Maven](http://wiki.gigaspaces.com/wiki/display/XAP9/Maven+Plugin) ejecutando <GIGASPACES_HOME>\tools\maven>installmavenrep.bat
%%. Crear proyecto de ejemplo utilizando el comando mostrado a continuación (todo en una línea):
	```
	mvn os:create 
		'-Dtemplate=basic-async-persistency'
		
		'-DgroupId=com.payulatam.samples.bank'
		
		'-DartifactId=bank' '-Dtemplate=basic-async-persistency'
	```
%%. Compilar y correr el proyecto para probar que este funcionando
	```
	mvn compile
	
	mvn os:run
	```
%%. Agregar archivo [.gitignore](http://www.bmchild.com/2012/06/git-ignore-for-java-eclipse-project.html)
%%. Crear repositorio git y realizar primer commit
	```
	git init
	
	git add .
	
	git commit -m "Initial commit"
	```
%%. Importar proyecto a Eclipse como proyecto Maven
	```
	File>Import>Existing Maven project into workspace
	```
	
%%. Crear nueva rama de desarrollo
	```
	git branch develop
	git checkout develop
	```
%%. Agregar clases del modelo al módulo *common*
	```
	Account.java
	
	Client.java
	
	Transaction.java
	
	TransactionType.java
	```
%%. Agregar clases al Sesion Factory (mirror/src/main/resources/META-INF/spring/pu.xml)
	```
	...

	<bean id="sessionFactory" class="org.springframework.orm.hibernate%%.annotation.AnnotationSessionFactoryBean">

        <property name="dataSource" ref="dataSource"/>

        <property name="annotatedClasses">

            <list>

                <value>com.payulatam.samples.bank.common.Data</value>

                <!-- * --><value>com.payulatam.samples.bank.common.Client</value>

                <!-- * --><value>com.payulatam.samples.bank.common.Account</value>

                <!-- * --><value>com.payulatam.samples.bank.common.Transaction</value>

            </list>

			...
	```
%%. Probar clases del negocio en feeder/.. ../Feeder.java

	```
	...
	
	Client client = new Client();
	
	client.setName("Simon");
	
	gigaSpace.write(data);
	
	gigaSpace.write(client);
	
	log.info("--- FEEDER WROTE " + data);
	
	log.info("--- NEW Client " + client);
	
	...
	```
	
%%. Modificar Test de processor para probar Clases del nuevo modelo (Client en vez de Data)
[processor/.. ../ProcessorTest.java]

%%. Probar clases de negocio en Processor
[processor/.. ../Processor.java]
 @SpaceDataEvent
    public Client processData(Client client) {
        // sleep to simulate some work
        try {
            Thread.sleep(workDuration);
        } catch (InterruptedException e) {
            // do nothing
        }
        client.setName("Santiago");
        log.info(" ------ PROCESSED : " + client);
        return client;
    }
	
	[processor/.. ../spring/pu.xml]
	<os-events:polling-container id="dataProcessorPollingEventContainer" giga-space="gigaSpace">
        <os-events:tx-support tx-manager="transactionManager"/>
        <os-core:template>
            <bean class="com.payulatam.samples.bank.common.Client">
                <property name="name" value="Simon"/>
            </bean>
        </os-core:template>
        <os-events:listener>
            <os-events:annotation-adapter>
                <os-events:delegate ref="dataProcessor"/>
            </os-events:annotation-adapter>
        </os-events:listener>
    </os-events:polling-container>

%%. Agregar dependencias de Spring MVC al pom.xml del proyecto bank
	```
		<!-- Spring MVC dependencies -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${springVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${springVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${springVersion}</version>
        </dependency>
	```
%%. Agregar ClientController
//TODO:



















