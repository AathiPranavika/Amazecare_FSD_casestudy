import { User } from "./User";

export interface Admin {
  adminId: number;
  userId:number;
  adminCode: string;          
  department: string;         
  qualification: string;      
  user: User; 
}                
