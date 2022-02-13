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

gamesDb.createCollection('levelInfo', {capped: false});

gamesDb.levelInfo.insertMany([
    {
        "difficulty": "EASY",
        "description": "Just 3 coins. Let's see who will be faster to get them.",
        "mazeHeight": 10,
        "mazeWidth": 10,
        "numberOfCoins": 3
    },
    {
        "difficulty": "MEDIUM",
        "description": "A classic 30x30 5-coin maze. Perfect to improve your coin-hunting skills or for a casual game.",
        "mazeHeight": 30,
        "mazeWidth": 30,
        "numberOfCoins": 5
    },
    {
        "difficulty": "HARD",
        "description": "Ready for a challenge? Compete with best coin hunters in this large and complex maze.",
        "mazeHeight": 60,
        "mazeWidth": 60,
        "numberOfCoins": 8
    },
]);
