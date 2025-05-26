## Carport Project for Semester 2

**Developed by:**
- Jonathan Kudsk – [cph-jk513@cphbusiness.dk](mailto:cph-jk513@cphbusiness.dk)  
- Marcus Forsberg – [cph-mf411@cphbusiness.dk](mailto:cph-mf411@cphbusiness.dk)  
- Jonas Outzen – [cph-jo221@cphbusiness.dk](mailto:cph-jo221@cphbusiness.dk)

---

## Info

> ⚠️ **Please note:**  
> You won't be able to send emails or write to the cloud database unless you have access to the required secret environment variables.

- **Seller Code (for demo/testing):** `1111`

### Description
This is a full-stack web application built for Johannes Fog to streamline the process of selling and managing carport offers. The system allows customers to design their own carport and request a non-binding offer, which is automatically sent to their email. Sellers have access to a dashboard where they can view, manage, and update offers. 
This project includes:
- A form where the customer can choose and type in their information before asking for an offer

- A way for the customer to dynmically view a technical drawing of their carport 

- A system that shows a price estimate based on what the customer has chosen

- A way for the customer to get a free offer by email without a list of parts

- A seller page where the seller can change the price and update dimensions on the customers offer

- If the customer accepts the offer, they will get a list of parts based on what the customer and seller agreed to

### Features
- Responsive web-design
- Dynamic offer generation
- Email delivery using SendGrid
- Seller page for updating data
- Cloud database integration
- Pdf material list generator

### Built with
- IntelliJ IDEA 2024.2.5 (Ultimate Edition)
- Java SDK (Corretto 17.0.14)
- Maven 17
- Javalin 6.5.0
- Thymeleaf 3.1.3
- JUnit 5.12.0
- Postgres 16.2 (via Docker)
- Docker
- PgAdmin 4 (8.13)
- SendGrid
- PDFBox 

### Getting Started
1. Clone the repo:
   ```bash
   https://github.com/MarcusPFF/Carport-Fog.git

2. Limited functionality
Since you don't have the database enviroment variable nor the SENDGRID_API_KEY, you'll have limited functionality.

### License ⚠️ 
This project is for educational use only.
