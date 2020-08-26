# Tech to Use
## Backend
- Database: MySQL / MongoDB
- Backend: SpringBoot
- Tool: Gradle
## Frontend
- ReactJS / AngularJS
## Test
- JUnit

# Router
- /main/{id}: main page, specified by user id
- /main/gone/{id}: main page with id after use's death
- /info/{id}: contact & message page, with id
- /remember/{id}: contact's page with user's id

# Backend
## Services needed
- Retrieve all contacts
- Retrieve all messages related to a contact
- Add a new contact
- Add a new messages with a list of contacts

## Database
Since we are using mongoDB, it's not relational.
We are still writing the design in a relational way.

### user
- id
- username
- password
- isAlive
- contacts 
    ```
    [
        {
            contact_id: id,
            name: "xxx",
            words: "a/sentence/to/this/content",
            contactsInfos: [
                {
                    type: "phone",
                    content: "1111111111",
                },
                {
                    type: "email",
                    content: "aaa@gmail.com",
                }
            ],
            messages: [
                {
                    content: "content",
                },...
            ]
        },
        { contact_id: ..., ... }, ...
    ]
    ```