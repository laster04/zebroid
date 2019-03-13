# Zebroid
## Simple martketplace clien for process new loans
Simple java bot checking new loans in marketplace

When you start application the scheduled job try every fifth minutes download new loans from  **Zonky marketplace API** [https://api.zonky.cz/loans/marketplace](https://api.zonky.cz/loans/marketplace). 
Except the first start every job will try download only latest loans after last processed loan. The first start download first batch of loans, the size of batch is configured in **_application.yml_**.

Downloaded loans are printed in console.
 > -- Na rekonstrukci -- \
   id: 420145, datePublish: 12-03-2019 - 19:34:06 \
   ref: https://app.zonky.cz/loan/420145, rating: AAAAA

If you want 

#### Used technology
 - java 8
 - Spring boot
 - Maven
 - SpringCloud openFeign
 - Scheduled jobs
#### How to build
  ```
  mvn clean install
  ```
#### How to run
  ```
  mvn spring-boot:run
  ```
#### Test
##### Used technology
 - Mockito
 - WireMock

Test with Spring context should be run with **offline** profile 