import { Game } from "../entities/Game";
export class OpenWager{
    selectedTeam: string;
    userName: string;
    userKey: string;
    amount: number;
    game: Game;
    status: string;
    opUserKey: string;
}