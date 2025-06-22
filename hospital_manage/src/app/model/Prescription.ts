export interface Prescription {
  prescriptionId: number;
  medicalRecordId: number;
  medicineName: string;
  dosage: string;
  remarks?: string;
}
