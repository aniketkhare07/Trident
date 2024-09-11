import { User } from "./user";

export class JwtAuthenticationResponse {
    token!: string;
    refreshToken!: string;
    userDto!: User;
}