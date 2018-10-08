import { Game } from "../entities/Game";
export class OpenWager{
    userName: string;
    userKey: string;
    amount: number;
    game: Game;
    status: string;
    opUserKey: string;
    selectedTeam: string;
    selected: string;
    uoValue: string;
    isWinner?:boolean;
}