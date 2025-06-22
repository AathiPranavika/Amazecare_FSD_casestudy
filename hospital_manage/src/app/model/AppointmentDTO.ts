export interface AppointmentDTO {
  appointmentId: number;
  appointmentDate: string;
  symptoms?: string;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELLED' | 'REJECTED' | 'COMPLETED';
  doctorId: number;
  patientId: number;
}
