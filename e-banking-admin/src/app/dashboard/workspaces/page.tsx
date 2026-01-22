'use client';

import PageContainer from '@/components/layout/page-container';
import { useTheme } from 'next-themes';
import { workspacesInfoContent } from '@/config/infoconfig';

export default function WorkspacesPage() {
  const { theme } = useTheme();
  const isDark = theme === 'dark';

  const mockWorkspaces = [
    { id: 'ws_1', name: 'Asmoth Tech' },
    { id: 'ws_2', name: 'Project X' }
  ];

  return (
    <PageContainer
      pageTitle='Workspaces'
      pageDescription='Manage your workspaces and switch between them'
      infoContent={workspacesInfoContent}
    >
      <div className='space-y-2'>
        {mockWorkspaces.map((ws) => (
          <div
            key={ws.id}
            className={`hover:bg-accent rounded-lg border p-4 ${
              isDark ? 'bg-dark-700 text-white' : 'bg-white text-black'
            }`}
          >
            {ws.name}
          </div>
        ))}
      </div>
    </PageContainer>
  );
}
