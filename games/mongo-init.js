db.createUser({
    user: 'coin-hunt',
    pwd: 'coin-hunt-auth',
    roles: [
        {
            role: 'readWrite',
            db: 'games-db',
        },
    ],
});

gamesDb = new Mongo().getDB("games-db");

gamesDb.createCollection('tests', { capped: false });

gamesDb.tests.insertMany([
    { "testId": 1, "name": "strange" },
    { "testId": 2, "name": "new" },
    { "testId": 3, "name": "high" },
    { "testId": 4, "name": "bottom" },
    { "testId": 5, "name": "up" }
]);
