import { User } from "./User";


export interface Doctor {
  doctorId: number;
  userId:number;
  specialization: string;
  experienceYears: number;
  qualification: string;
  designation: string;
  user: User;
}

