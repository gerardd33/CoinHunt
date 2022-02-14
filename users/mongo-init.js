db.createUser({
    user: 'coin-hunt',
    pwd: 'coin-hunt-auth',
    roles: [
        {
            role: 'readWrite',
            db: 'users-db',
        },
    ],
});

usersDb = new Mongo().getDB("users-db");

usersDb.createCollection('userData', {capped: false});

usersDb.userData.insertMany([
    {
        "userId": "___example_user",
        "email": "user@gmail.com",
        "password": "54321"
    }
]);
