export interface ICircuit {

  id: number;
  circuit_key: number
  circuit_short_name: string,
  country_code: string,
  country_key: number,
  country_name: string,
  date_start: Date,
  gmt_offset: Date,
  location: string,
  meeting_key: number,
  meeting_name: string,
  meeting_official_name: string,
  year: number

}
