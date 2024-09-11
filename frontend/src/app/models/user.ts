import { Role } from "./role";

export class User {
    userid!: number;
    name!: string;
    username!: string;
    password!: string;
    contact!: string;
    active!: boolean;
    role!: Role;
}