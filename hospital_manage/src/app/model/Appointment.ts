import { Doctor } from "./Doctor";
import { Patient } from "./Patient";

export interface Appointment {
  appointmentId: number;
  patient: Patient;
  doctor: Doctor;
  appointmentDate: string;
  symptoms?: string;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELLED' | 'REJECTED' | 'COMPLETED';
  createdAt?: string;                  
}
