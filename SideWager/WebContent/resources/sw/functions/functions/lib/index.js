"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
const functions = require("firebase-functions");
const https = require("https");
const admin = require("firebase-admin");
admin.initializeApp();
// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
//host: 'jsonodds.com'
//path: '/api/odds',
// const options = {
//   host: 'jsonplaceholder.typicode.com',
//   port:443,
//   path: '/todos/1',
//   headers: {
//     'x-api-key': 'a68b2a88-6a80-4b09-b5d2-c83f4b5896d0'
//   },
//   method: 'GET',
//   family: 4
// }
exports.hourly_job =
    functions.pubsub.topic('game_data').onPublish((event) => {
        console.log("This game_data job is ran every hour!");
    });
exports.getGameData = functions.https.onRequest((request, response) => {
    try {
        const options = {
            host: 'jsonplaceholder.typicode.com',
            path: '/posts',
            method: 'GET',
            family: 4,
            headers: {
                'user-agent': 'Mozilla/5.0'
            }
        };
        console.log(options);
        let rData = "";
        https.request(options, (res) => {
            console.log(res);
            res.setEncoding('utf8');
            console.log("response received!!");
            res.on('data', (d) => {
                rData += d;
                console.log("got partial data!! msg length: " + rData.length);
            });
            res.on('end', function () {
                console.log("game_data--------    " + rData);
                response.send("game data received");
            });
        }).on('error', (e) => {
            const err = "Got error:" + e.message;
            console.log(err);
            response.send(err);
        });
    }
    catch (error) {
        console.log("catch: " + error);
    }
    console.log("This game_data http job is executed every 10min!!");
});
exports.checkGameResult = functions.https.onRequest((request, response) => {
    const date = request.query.a;
    console.log(date);
    admin.database().ref('activegames/' + date).once('value').then(result => {
        const games = result.val();
        const gamearray = [];
        for (const game in games) {
            if (games.hasOwnProperty(game)) {
                console.log(games + "->" + games[game]);
                const gameValue = games[game];
                gamearray.push(gameValue);
                console.log(gameValue['matchTime']);
            }
        }
        response.send(gamearray);
    }).catch(error => {
        response.status(500).status(error);
    });
});
exports.updateOpenGameResult = functions.https.onRequest((request, response) => __awaiter(this, void 0, void 0, function* () {
    try {
        const gameId = request.query.a;
        const homeScore = request.query.b;
        const awayScore = request.query.c;
        const gameDate = request.query.d;
        const gameFinished = request.query.e;
        let isGameFinshed = false;
        if (gameFinished === 'yes') {
            isGameFinshed = true;
        }
        let cleardb = false;
        console.log("gameId: " + gameId + ".....home score: " + homeScore + ".....away score: " + awayScore);
        const result = yield admin.database().ref('wagerinfo/' + gameId).once('value');
        const games = result.val();
        const promises = [];
        for (const game in games) {
            const gameValue = games[game];
            const userId = gameValue['userId'];
            const wagerId = gameValue['wagerId'];
            const p = admin.database().ref('openwager/' + userId + "/" + wagerId).once('value');
            promises.push(p);
        }
        const snapshots = yield Promise.all(promises);
        const updatePromises = [];
        const removePromises = [];
        const scorePromises = [];
        snapshots.forEach(snaps => {
            const openwager = snaps.val();
            const wagerId = snaps.key;
            const selected = openwager.selected;
            const uoValue = openwager.uoValue;
            let userWinner;
            console.log("selected: " + selected);
            console.log("uoValue: " + uoValue);
            if ((!(selected == null)) && (selected.includes("home") || selected.includes("away"))) { //user selects team
                console.log("user selects team");
                if (homeScore > awayScore) {
                    if (selected === "home") {
                        userWinner = "true";
                    }
                    else {
                        userWinner = "false";
                    }
                }
                else if (awayScore > homeScore) {
                    if (selected === "away") {
                        userWinner = "true";
                    }
                    else {
                        userWinner = "false";
                    }
                }
                else if (awayScore === homeScore) {
                    userWinner = "draw";
                }
            }
            else if ((!(uoValue == null)) && (uoValue.includes("o") || uoValue.includes("u"))) { //user selects under/Over
                const total = homeScore + awayScore;
                console.log("user selects under/Over total: " + total);
                if (total > selected) {
                    if (uoValue.includes("o")) {
                        userWinner = "true";
                    }
                    else {
                        userWinner = "false";
                    }
                }
                else if (total < selected) {
                    if (uoValue.includes("o")) {
                        userWinner = "true";
                    }
                    else {
                        userWinner = "false";
                    }
                }
                else if (total === selected) {
                    userWinner = "draw";
                }
            }
            if (!(openwager == null)) {
                const status = openwager.status;
                if (status.includes('Challenged') || status.includes('Pending') || status.includes('Rejected')) {
                    let userId = "";
                    if (status.includes('Pending')) {
                        userId = openwager.opUserKey;
                    }
                    if (status.includes('Challenged') || status.includes('Rejected')) {
                        userId = openwager.userKey;
                    }
                    const p = admin.database().ref('openwager/' + userId + "/" + wagerId).remove();
                    removePromises.push(p);
                    cleardb = true;
                }
                let score = {};
                if (status.includes('Accepted') && status.includes('challenge')) {
                    const userId = openwager.opUserKey;
                    if (userWinner === "true") {
                        openwager.status = "You loose";
                        openwager.isWinner = false;
                        score.value = openwager.amount;
                        score.event = "sub";
                        const scorePromiseOpSub = admin.database().ref('scores/' + userId).push(score);
                        scorePromises.push(scorePromiseOpSub);
                        cleardb = true;
                    }
                    else if (userWinner === "false") {
                        openwager.status = "You won";
                        openwager.isWinner = true;
                        score.value = openwager.amount;
                        score.event = "add";
                        const scorePromiseOpAdd = admin.database().ref('scores/' + userId).push(score);
                        scorePromises.push(scorePromiseOpAdd);
                        cleardb = true;
                    }
                    else if (userWinner === "draw") {
                        //TODO logic for draw
                    }
                    const updateOpPromise = admin.database().ref('openwager/' + userId + "/" + wagerId).set(openwager);
                    updatePromises.push(updateOpPromise);
                }
                if (status.includes('Accepted') && !status.includes('challenge')) {
                    const userId = openwager.userKey;
                    if (userWinner === "true") {
                        openwager.status = "You won";
                        openwager.isWinner = true;
                        score.value = openwager.amount;
                        score.event = "add";
                        const scorePromiseAdd = admin.database().ref('scores/' + userId).push(score);
                        scorePromises.push(scorePromiseAdd);
                        cleardb = true;
                    }
                    else if (userWinner === "false") {
                        openwager.status = "You loose";
                        openwager.isWinner = false;
                        score.value = openwager.amount;
                        score.event = "sub";
                        const scorePromiseSub = admin.database().ref('scores/' + userId).push(score);
                        scorePromises.push(scorePromiseSub);
                        cleardb = true;
                    }
                    else if (userWinner === "draw") {
                        //TODO logic for draw
                    }
                    const updatePromise = admin.database().ref('openwager/' + userId + "/" + wagerId).set(openwager);
                    updatePromises.push(updatePromise);
                }
            }
        });
        yield Promise.all(removePromises);
        yield Promise.all(updatePromises);
        yield Promise.all(scorePromises);
        if (cleardb) {
            //remove game from active
            //remove game from wagerInfo
        }
        response.send("open wager updated");
    }
    catch (error) {
        response.status(500).status(error);
    }
}));
exports.updateLiveGameResult = functions.https.onRequest((request, response) => __awaiter(this, void 0, void 0, function* () {
    try {
        const gameId = request.query.a;
        const clearDb = request.query.b;
        if (clearDb === 'yes') {
            //remove game from active
            //remove game from wagerInfo
        }
        const result = yield admin.database().ref('wagerinfo/' + gameId).once('value');
        const games = result.val();
        const promises = [];
        for (const game in games) {
            const gameValue = games[game];
            const userId = gameValue['userId'];
            const wagerId = gameValue['wagerId'];
            const p = admin.database().ref('livewager/' + userId + "/" + wagerId).remove();
            promises.push(p);
        }
        yield Promise.all(promises);
        response.send("games updated");
    }
    catch (error) {
        response.status(500).status(error);
    }
    //promise way of coding
    /*const gameId:string = request.query.game;
    admin.database().ref('wagerinfo/'+gameId).once('value').then(result=>{
      const games = result.val();
      const promises = [];
      for(const game in games){
        const gameValue = games[game];
        const userId = gameValue['userId'];
        const wagerId = gameValue['wagerId'];
        const p = admin.database().ref('livewager/'+userId+"/"+wagerId).remove();
        promises.push(p)
      }
      return Promise.all(promises);
    }).then(snapshots=>{
      response.send("games updated");
    })
    .catch(error=>{
      response.status(500).status(error);
    });*/
}));
exports.onScoreCreate = functions.database.ref('/scores/{userId}/{scoreId}')
    .onCreate((snapshot, context) => __awaiter(this, void 0, void 0, function* () {
    const userId = context.params.userId;
    const scoreId = context.params.scoreId;
    if (scoreId === 'score') {
        return null;
    }
    console.log("user: " + userId + "......" + "scoreId: " + scoreId);
    const score = snapshot.val();
    const scoreRef = admin.database().ref('/scores/' + userId + "/score");
    const oldScorePromise = yield scoreRef.once('value');
    const oldScore = oldScorePromise.val();
    let newScore = oldScore;
    if (oldScore == null) {
        newScore = 0;
    }
    if (score.event === 'add') {
        newScore = oldScore + score.value;
    }
    if (score.event === 'sub') {
        newScore = oldScore - score.value;
    }
    yield scoreRef.set(newScore);
    return snapshot.ref.remove();
}));
//# sourceMappingURL=index.js.map