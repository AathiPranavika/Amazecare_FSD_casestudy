import { Appointment } from "./Appointment";
import { User } from "./User";

export interface Patient {
  patientId: number;
  userId:number;
  user: User;  
  address?: string;
  emergencyContact: string;
  bloodGroup: 'A+' | 'A-' | 'B+' | 'B-' | 'O+' | 'O-' | 'AB+' | 'AB-';
  medicalHistory?: string;
  appointments: Appointment[];
}
