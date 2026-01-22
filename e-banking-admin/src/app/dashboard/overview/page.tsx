import PageContainer from "@/components/layout/page-container";
import TestComponent from "@/features/accounts/components/TestComponent";

export default function Page() {

    return <PageContainer
      scrollable={false}
      pageTitle='Token Handler Pattern'
      pageDescription='Using BFF with Spring Cloud Gateway'
    >
      <h1>Hello</h1>
      <TestComponent/>
    </PageContainer>
}