import { Admin } from "./Admin";
import { Doctor } from "./Doctor";
import { Patient } from "./Patient";

export interface User {
  userId: number;
  name: string;
  email: string;
  password: string;
  role: 'PATIENT' | 'DOCTOR' | 'ADMIN';
  gender: 'Male' | 'Female' | 'Other';
  dateOfBirth: string;
  contactNumber: string;
 createdAt?: string;
  patient?: Patient;
  doctor?: Doctor;
  admin?: Admin;
}
