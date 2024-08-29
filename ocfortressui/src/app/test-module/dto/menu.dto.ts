export type TestCase = {
  testCaseId: string;
  menus: Menu[];
};

export type Menu = {
  menuName: string;
  fields: Field[];
};

export type Field = {
  label: string;
  fieldId: string;
  defaultValue: string;
  readOnly:string;
  newValue: string;
}
