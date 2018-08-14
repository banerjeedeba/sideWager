import * as functions from 'firebase-functions';
import * as https from 'https';

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
// export const helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
//host: 'jsonodds.com'
//path: '/api/odds',

exports.hourly_job =
  functions.pubsub.topic('game_data').onPublish((event) => {
    console.log("This game_data job is ran every hour!")
  });

export const getGameData = functions.https.onRequest((request, response) => {

  try{
  const options = {
    host: 'jsonodds.com',
    port:443,
    path: '/api/odds',
    headers: {
      'x-api-key': 'a68b2a88-6a80-4b09-b5d2-c83f4b5896d0'
    },
    method: 'GET',
    family: 4
    
  }


  let rData=""
  https.request(options,(res)=>{
    console.log(res);
    res.setEncoding('utf8');
    console.log("response received!!");
          res.on('data',(d) =>{
              rData+=d;
              console.log("got partial data!! msg length: "+rData.length);
           });
           res.on('end',function(){
                console.log("game_data--------    "+rData);
                response.send("game data received");
              });
    
  }).on('error', (e) => {
    const err= "Got error:"+e.message;
    console.log(err);
    response.send(err);
});
} catch(error){
  console.log("catch: "+error);
}
  
console.log("This game_data http job is executed every 10min!!");  
});