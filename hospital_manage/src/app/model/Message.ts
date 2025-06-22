export interface MessageDTO {
  messageId: number;         
  doctorId: number;
  patientId: number;
  senderRole: 'DOCTOR' | 'PATIENT';  
  message: string;
  sentAt?: string;            
  read?: boolean;             
}
