export type TestCaseDataType = {
  testCaseCheckBox: boolean
  testCase: string;
  executedOn: any;
  status: string;
  policy: string;
  transactionNum: string;
  executedBy: string;
};



export const TestCaseDataTypeConvertor = (
  testData: any[],
) =>
  testData
    ? testData.map((data) => ({
      testCaseCheckBox: data.testCaseCheckBox,
      testCase: data.transactionCd,
      executedOn: data.executedOn,
      status: data.statusCd,
      policy: data.policyNumber,
      transactionNum: data.transactionNumber,
      executedBy: data.executedBy,
    }))
    : [];
